package br.com.uesb.ceasadigital.api.features.carrinho.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.uesb.ceasadigital.api.common.exceptions.BadRequestException;
import br.com.uesb.ceasadigital.api.common.exceptions.InvalidCarrinhoOperationException;
import br.com.uesb.ceasadigital.api.features.carrinho.dto.response.CarrinhoResponseDTO;
import br.com.uesb.ceasadigital.api.features.carrinho.mapper.CarrinhoMapper;
import br.com.uesb.ceasadigital.api.features.carrinho.model.Carrinho;
import br.com.uesb.ceasadigital.api.features.carrinho.model.enums.CarrinhoStatus;
import br.com.uesb.ceasadigital.api.features.carrinho.repository.CarrinhoRepository;
import br.com.uesb.ceasadigital.api.features.item_carrinho.model.ItemCarrinho;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import br.com.uesb.ceasadigital.api.features.user.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("CarrinhoService Unit Tests - Detailed")
class CarrinhoServiceTest {

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Mock
    private UserService userService;

    @Mock
    private CarrinhoMapper carrinhoMapper;

    @InjectMocks
    private CarrinhoService service;

    private User user;
    private Carrinho carrinho;
    private CarrinhoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        // usuário fictício
        user = new User();
        user.setId(1L);
        user.setName("Ana");

        // carrinho ativo do usuário
        carrinho = new Carrinho(user);
        carrinho.setId(1L);
        carrinho.setStatus(CarrinhoStatus.ATIVO);
        carrinho.setItens(new ArrayList<>());

        responseDTO = new CarrinhoResponseDTO();
        responseDTO.setId(1L);

    }

    @Test
    @DisplayName("Should return carrinho for current user")
    void findCarrinho_ShouldReturnCarrinho() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findByUsuarioIdAndStatus(user.getId(), CarrinhoStatus.ATIVO))
                .thenReturn(Optional.of(carrinho));
        when(carrinhoMapper.toResponseDTO(carrinho)).thenReturn(responseDTO);

        CarrinhoResponseDTO result = service.findCarrinho();

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(carrinhoRepository).findByUsuarioIdAndStatus(user.getId(), CarrinhoStatus.ATIVO);
        verify(carrinhoMapper).toResponseDTO(carrinho);
    }

    @Test
    @DisplayName("Should create new carrinho when none exists")
    void getCarrinho_ShouldCreateNewCarrinho() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findByUsuarioIdAndStatus(user.getId(), CarrinhoStatus.ATIVO))
                .thenReturn(Optional.empty());
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        Carrinho result = service.getCarrinho();

        assertNotNull(result);
        assertEquals(CarrinhoStatus.ATIVO, result.getStatus());
        verify(carrinhoRepository).save(any(Carrinho.class));
    }

    @Test
    @DisplayName("Should clear carrinho with items")
    void clearUserCarrinho_ShouldClearItems() {
        carrinho.getItens().add(new ItemCarrinho()); // adiciona item fictício
        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findByUsuarioIdAndStatus(user.getId(), CarrinhoStatus.ATIVO))
                .thenReturn(Optional.of(carrinho));

        service.clearUserCarrinho();

        assertTrue(carrinho.getItens().isEmpty());
    }

    @Test
    @DisplayName("Should throw InvalidCarrinhoOperationException when clearing empty carrinho")
    void clearUserCarrinho_ShouldThrow_WhenEmpty() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findByUsuarioIdAndStatus(user.getId(), CarrinhoStatus.ATIVO))
                .thenReturn(Optional.of(carrinho));

        assertThrows(InvalidCarrinhoOperationException.class, () -> service.clearUserCarrinho());
    }

    @Test
    @DisplayName("Should throw BadRequestException when clearing finalized carrinho")
    void clearUserCarrinho_ShouldThrow_WhenFinalized() {
        carrinho.setStatus(CarrinhoStatus.FINALIZADO);
        carrinho.getItens().add(new ItemCarrinho()); // garante que não está vazio
        when(userService.getCurrentUser()).thenReturn(user);
        when(carrinhoRepository.findByUsuarioIdAndStatus(user.getId(), CarrinhoStatus.ATIVO))
                .thenReturn(Optional.of(carrinho));

        assertThrows(BadRequestException.class, () -> service.clearUserCarrinho());
    }

    @Test
    @DisplayName("Should finalize carrinho successfully")
    void finalizarCarrinho_ShouldMarkAsFinalizado() {
        when(carrinhoRepository.save(any(Carrinho.class))).thenReturn(carrinho);

        service.finalizarCarrinho(carrinho);

        assertEquals(CarrinhoStatus.FINALIZADO, carrinho.getStatus());
        verify(carrinhoRepository).save(carrinho);
    }
}
