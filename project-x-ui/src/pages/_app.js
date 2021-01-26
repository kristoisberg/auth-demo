import React from "react";
import App from "next/app";
import PropTypes from "prop-types";
import nextCookie from "next-cookies";
import { decode } from "jsonwebtoken";
import Layout from "components/layout";
import AuthenticationContext from "../auth/AuthenticationContext";
import "abortcontroller-polyfill/dist/polyfill-patch-fetch";

const ProjectXApp = ({ Component, token, user, pageProps }) => {
  return (
    <AuthenticationContext.Provider value={{ token, user }}>
      <Layout>
        <Component token={token} user={user} {...pageProps} />
      </Layout>
    </AuthenticationContext.Provider>
  );
};

ProjectXApp.getInitialProps = async (ctx) => {
  const appProps = await App.getInitialProps(ctx);
  const { token } = nextCookie(ctx.ctx, { doNotParse: true });
  const user = token == null ? null : decode(token);
  return { ...appProps, token, user };
};

ProjectXApp.propTypes = {
  Component: PropTypes.func.isRequired,
  token: PropTypes.string,
  user: PropTypes.object,
  pageProps: PropTypes.object.isRequired,
};

ProjectXApp.defaultProps = {
  token: null,
  user: null,
};

export default ProjectXApp;
