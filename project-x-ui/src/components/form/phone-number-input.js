import React from "react";
import { Input } from "formik-antd";
import PropTypes from "prop-types";
import { COUNTRY_PHONE_CODES } from "constants/countries";
import InputWrapper from "./input-wrapper";

const PhoneNumberInput = ({ name, label, placeholder, countryCode, plain, ...props }) => {
  return (
    <InputWrapper name={name} label={label} plain={plain}>
      <Input name={name} placeholder={placeholder || label} addonBefore={COUNTRY_PHONE_CODES[countryCode]} {...props} />
    </InputWrapper>
  );
};

PhoneNumberInput.defaultProps = {
  placeholder: null,
  plain: false,
};

PhoneNumberInput.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  placeholder: PropTypes.string,
  countryCode: PropTypes.string.isRequired,
  plain: PropTypes.bool,
};

export default PhoneNumberInput;
