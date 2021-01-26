import React from "react";
import logout from "../auth/logout";

const Logout = () => <div className="container">aaaaaaaaaaaaaa</div>;

Logout.getInitialProps = async ({ ctx }) => {
  logout(ctx);
  return {};
};

export default Logout;
