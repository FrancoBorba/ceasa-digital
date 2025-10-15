import { Link } from "react-router";


function AuthLinkText({ children, link }) {
  return (
    <Link to={link} className="text-left text-[#1F4B1D] underline text-xs">
      {children}
    </Link>
  );
}

export default AuthLinkText;