import Router from "next/router";

const redirectToLogin = (ctx) => {
  if (typeof window !== "undefined") {
    Router.push("/login");
  } else if (ctx) {
    ctx.res.writeHead(302, { Location: "/login" });
    ctx.res.end();
  }
};

export default redirectToLogin;
