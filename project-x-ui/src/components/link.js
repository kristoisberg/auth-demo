/* eslint-disable jsx-a11y/anchor-is-valid */
import React from "react";
import NextLink from "next/link";
import PropTypes from "prop-types";

const Link = ({ children, ...props }) => (
  <NextLink {...props}>
    <a>{children}</a>
  </NextLink>
);

Link.propTypes = {
  children: PropTypes.node.isRequired,
};

export default Link;
