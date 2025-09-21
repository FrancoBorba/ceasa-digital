#!/bin/bash

# Script para executar testes com visualização melhorada
# Autor: Gerado automaticamente
# Data: $(date +%Y-%m-%d)

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
WHITE='\033[1;37m'
NC='\033[0m' # No Color

# Função para imprimir cabeçalho
print_header() {
    echo -e "${CYAN}╔══════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${CYAN}║${WHITE}                    CEASA DIGITAL - TESTES                    ${CYAN}║${NC}"
    echo -e "${CYAN}╚══════════════════════════════════════════════════════════════╝${NC}"
    echo ""
}

# Função para imprimir seção
print_section() {
    echo -e "${BLUE}▶ $1${NC}"
    echo -e "${BLUE}$(printf '─%.0s' {1..60})${NC}"
}

# Função para extrair detalhes dos testes do output do Maven
extract_test_details() {
    local test_output="$1"
    
    # Extrair informações detalhadas dos testes
    echo "$test_output" | grep -E "(Running|Tests run:)" | while read -r line; do
        if [[ $line == *"Running"* ]]; then
            # Extrair nome da classe de teste
            local class_name=$(echo "$line" | sed 's/.*Running //' | sed 's/br\.com\.uesb\.ceasadigital\.api\.//')
            echo "CLASS:$class_name"
        elif [[ $line == *"Tests run:"* ]]; then
            # Extrair resultados dos testes
            echo "RESULT:$line"
        fi
    done
}

# Função para mostrar barra de progresso
show_progress_bar() {
    local percentage=$1
    local width=50
    local filled=$((percentage * width / 100))
    local empty=$((width - filled))
    
    printf "["
    printf "%*s" $filled | tr ' ' '█'
    printf "%*s" $empty | tr ' ' '░'
    printf "] %d%%\n" $percentage
}

# Função para mostrar resumo visual dos testes
show_test_summary() {
    local test_output="$1"
    
    # Extrair informações dos testes
    local total_tests=$(echo "$test_output" | grep -o "Tests run: [0-9]*" | tail -1 | grep -o "[0-9]*")
    local failures=$(echo "$test_output" | grep -o "Failures: [0-9]*" | tail -1 | grep -o "[0-9]*")
    local errors=$(echo "$test_output" | grep -o "Errors: [0-9]*" | tail -1 | grep -o "[0-9]*")
    local skipped=$(echo "$test_output" | grep -o "Skipped: [0-9]*" | tail -1 | grep -o "[0-9]*")
    
    local passed=$((total_tests - failures - errors - skipped))
    
    echo -e "${WHITE}📊 RESUMO DOS TESTES${NC}"
    echo -e "${WHITE}$(printf '═%.0s' {1..30})${NC}"
    echo -e "Total de testes: ${CYAN}$total_tests${NC}"
    echo -e "✅ Passou: ${GREEN}$passed${NC}"
    echo -e "❌ Falhou: ${RED}$failures${NC}"
    echo -e "⚠️  Erro: ${YELLOW}$errors${NC}"
    echo -e "⏭️  Pulou: ${BLUE}$skipped${NC}"
    echo ""
    
    # Calcular taxa de sucesso
    if [ "$total_tests" -gt 0 ]; then
        local success_rate=$((passed * 100 / total_tests))
        echo -e "${WHITE}Taxa de Sucesso:${NC}"
        if [ "$success_rate" -eq 100 ]; then
            echo -e "${GREEN}"
        elif [ "$success_rate" -ge 80 ]; then
            echo -e "${YELLOW}"
        else
            echo -e "${RED}"
        fi
        show_progress_bar $success_rate
        echo -e "${NC}"
    fi
}

# Função para extrair testes que falharam do output
extract_failed_tests() {
    local test_output="$1"
    
    # Método 1: Extrair do output de erro do Maven
    echo "$test_output" | grep -E "FAILURE!|ERROR!" | grep -E "\.java.*--" | while read -r line; do
        local full_method=$(echo "$line" | sed 's/.*\] //' | sed 's/ -- Time elapsed.*//')
        echo "$full_method"
    done
    
    # Método 2: Extrair da seção "Failures:" do Maven
    echo "$test_output" | grep -A 10 "Failures:" | grep -E "Test.*:" | while read -r line; do
        local method_info=$(echo "$line" | sed 's/.*Test\.//' | sed 's/:.*//')
        echo "UserServiceTest.$method_info"
    done
}

# Função para mostrar detalhes dos testes por classe
show_test_details() {
    local test_output="$1"
    
    echo -e "${WHITE}🧪 DETALHES DOS TESTES POR CLASSE${NC}"
    echo -e "${WHITE}$(printf '═%.0s' {1..40})${NC}"
    
    # Extrair testes que falharam
    local failed_tests=$(extract_failed_tests "$test_output")
    
    # Processar cada classe de teste
    find backend/src/test/java -name "*Test.java" | while read -r test_file; do
        local class_name=$(basename "$test_file" .java)
        local short_class_name=$(echo "$class_name" | sed 's/.*\.//')
        
        echo -e "${CYAN}📋 $short_class_name${NC}"
        echo -e "${CYAN}$(printf '─%.0s' {1..30})${NC}"
        
        # Extrair métodos @Test do arquivo
        local test_methods=$(grep -n "@Test" "$test_file" -A 2 | grep "void " | sed 's/.*void //' | sed 's/(.*$//' | sort)
        
        if [ -n "$test_methods" ]; then
            while IFS= read -r method; do
                if [ -n "$method" ]; then
                    # Verificar se este método específico falhou
                    local full_method_name="$class_name.$method"
                    if echo "$failed_tests" | grep -q "$full_method_name"; then
                        echo -e "  ${RED}❌ $method${NC}"
                        
                        # Extrair número da linha da falha do output do Maven
                        # Primeiro tenta da seção "Failures:"
                        local line_number=$(echo "$test_output" | grep -E "Failures:" -A 20 | grep "$full_method_name" | grep -o ":[0-9]*" | sed 's/://' | head -1)
                        
                        # Se não encontrou, tenta do stack trace
                        if [ -z "$line_number" ]; then
                            line_number=$(echo "$test_output" | grep "$full_method_name" | grep "\.java:" | sed 's/.*\.java:\([0-9]*\).*/\1/' | head -1)
                        fi
                        
                        # Mostrar detalhes da falha com linha
                        local failure_detail=$(echo "$test_output" | grep -A 5 "$full_method_name" | grep -E "AssertionFailedError|expected|but was" | head -1)
                        if [ -n "$failure_detail" ]; then
                            if [ -n "$line_number" ] && [[ "$line_number" =~ ^[0-9]+$ ]]; then
                                echo -e "    ${YELLOW}↳ Linha $line_number: $(echo "$failure_detail" | sed 's/^[[:space:]]*//')${NC}"
                            else
                                echo -e "    ${YELLOW}↳ $(echo "$failure_detail" | sed 's/^[[:space:]]*//')${NC}"
                            fi
                        fi
                        
                        # Tentar mostrar o código da linha que falhou
                        if [ -n "$line_number" ] && [[ "$line_number" =~ ^[0-9]+$ ]]; then
                            local code_line=$(sed -n "${line_number}p" "$test_file" 2>/dev/null | sed 's/^[[:space:]]*//')
                            if [ -n "$code_line" ]; then
                                echo -e "    ${PURPLE}📝 $code_line${NC}"
                            fi
                        fi
                    else
                        echo -e "  ${GREEN}✅ $method${NC}"
                    fi
                fi
            done <<< "$test_methods"
        fi
        
        # Contar testes manualmente baseado no que foi exibido
        local total_methods=0
        local passed_methods=0
        local failed_methods=0
        
        # Contar métodos @Test no arquivo
        if [ -n "$test_methods" ]; then
            while IFS= read -r method; do
                if [ -n "$method" ]; then
                    total_methods=$((total_methods + 1))
                    
                    # Verificar se este método específico falhou
                    local full_method_name="$class_name.$method"
                    if echo "$failed_tests" | grep -q "$full_method_name"; then
                        failed_methods=$((failed_methods + 1))
                    else
                        passed_methods=$((passed_methods + 1))
                    fi
                fi
            done <<< "$test_methods"
        fi
        
        # Mostrar estatísticas corretas
        if [ "$total_methods" -gt 0 ]; then
            echo -e "  ${WHITE}Resumo: ${GREEN}$passed_methods passou${NC}, ${RED}$failed_methods falhou${NC}, ${YELLOW}0 erro${NC}"
        fi
        echo ""
    done
}


# Função principal
main() {
    clear
    print_header
    
    print_section "Executando testes..."
    
    # Navegar para o diretório do backend
    cd backend
    
    # Executar testes e capturar output
    echo -e "${YELLOW}Executando mvn clean test...${NC}"
    test_output=$(mvn clean test 2>&1)
    test_exit_code=$?
    
    # Voltar ao diretório raiz
    cd ..
    
    echo ""
    print_section "Resultados"
    
    # Mostrar resumo dos testes
    show_test_summary "$test_output"
    
    # Sempre mostrar detalhes dos testes (passaram ou falharam)
    show_test_details "$test_output"
    
    # Mostrar relatórios disponíveis
    echo -e "${WHITE}📁 RELATÓRIOS DISPONÍVEIS${NC}"
    echo -e "${WHITE}$(printf '═%.0s' {1..30})${NC}"
    echo -e "🌐 Relatório HTML: ${CYAN}backend/target/site/jacoco/index.html${NC}"
    echo -e "📄 Relatório XML: ${CYAN}backend/target/site/jacoco/jacoco.xml${NC}"
    echo -e "📊 Relatório CSV: ${CYAN}backend/target/site/jacoco/jacoco.csv${NC}"
    echo ""
    
    if [ $test_exit_code -eq 0 ]; then
        # Perguntar se quer abrir o relatório apenas se todos os testes passaram
        echo -e "${YELLOW}Deseja abrir o relatório de cobertura no navegador? (y/n)${NC}"
        read -r response
        if [[ "$response" =~ ^[Yy]$ ]]; then
            if command -v open &> /dev/null; then
                open backend/target/site/jacoco/index.html
            elif command -v xdg-open &> /dev/null; then
                xdg-open backend/target/site/jacoco/index.html
            else
                echo -e "${RED}Não foi possível abrir o navegador automaticamente${NC}"
                echo -e "Abra manualmente: ${CYAN}backend/target/site/jacoco/index.html${NC}"
            fi
        fi
        
        echo -e "${GREEN}✅ Todos os testes passaram com sucesso!${NC}"
    else
        echo -e "${RED}❌ Alguns testes falharam. Verifique os detalhes acima nos testes marcados com ❌${NC}"
        echo -e "${YELLOW}💡 Dica: Abra o relatório HTML para mais detalhes sobre a cobertura${NC}"
        
        exit 1
    fi
    
    echo ""
    echo -e "${CYAN}╔══════════════════════════════════════════════════════════════╗${NC}"
    echo -e "${CYAN}║${WHITE}                    EXECUÇÃO CONCLUÍDA                       ${CYAN}║${NC}"
    echo -e "${CYAN}╚══════════════════════════════════════════════════════════════╝${NC}"
}

# Executar função principal
main "$@"
