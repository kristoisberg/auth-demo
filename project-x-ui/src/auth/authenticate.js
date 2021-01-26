import Router from "next/router";
import cookie from "js-cookie";

const authenticate = (token) => {
  cookie.set("token", token, { expires: 1, sameSite: "None" });
  Router.push("/profile");
};

export default authenticate;
