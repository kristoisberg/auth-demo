import React from "react";
import { Input } from "formik-antd";
import PropTypes from "prop-types";
import InputWrapper from "./input-wrapper";

const PasswordInput = ({ name, label, placeholder, plain, ...props }) => (
  <InputWrapper name={name} label={label} plain={plain}>
    <Input.Password name={name} placeholder={placeholder || label} {...props} />
  </InputWrapper>
);

PasswordInput.defaultProps = {
  placeholder: null,
  plain: false,
};

PasswordInput.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string,
  plain: PropTypes.bool,
};

export default PasswordInput;
