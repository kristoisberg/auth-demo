import cookie from "js-cookie";
import redirectToLogin from "./redirectToLogin";

const logout = (ctx) => {
  cookie.remove("token");
  redirectToLogin(ctx);
};

export default logout;
