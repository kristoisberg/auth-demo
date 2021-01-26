import React from "react";
import { Select } from "formik-antd";
import PropTypes from "prop-types";
import { COUNTRIES, COUNTRY_NAMES } from "constants/countries";
import Flag from "components/flag";
import InputWrapper from "./input-wrapper";
import "./country-input.less";

const CountryInput = ({ name, label, plain }) => {
  return (
    <InputWrapper name={name} label={label} plain={plain}>
      <Select name={name} className="country-input">
        {COUNTRIES.map((countryCode) => (
          <Select.Option value={countryCode} key={countryCode}>
            <Flag countryCode={countryCode} /> {COUNTRY_NAMES[countryCode]}
          </Select.Option>
        ))}
      </Select>
    </InputWrapper>
  );
};

CountryInput.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  plain: PropTypes.bool,
};

CountryInput.defaultProps = {
  plain: false,
};

export default CountryInput;
