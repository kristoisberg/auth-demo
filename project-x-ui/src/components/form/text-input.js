import React from "react";
import { Input } from "formik-antd";
import PropTypes from "prop-types";
import InputWrapper from "./input-wrapper";

const TextInput = ({ name, label, placeholder, plain, ...props }) => (
  <InputWrapper name={name} label={label} plain={plain}>
    <Input name={name} placeholder={placeholder || label} {...props} />
  </InputWrapper>
);

TextInput.defaultProps = {
  placeholder: null,
  plain: false,
};

TextInput.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string,
  plain: PropTypes.bool,
};

export default TextInput;
