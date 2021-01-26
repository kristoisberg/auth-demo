import React from "react";
import classNames from "classnames";
import PropTypes from "prop-types";
import "flag-icon-css/less/flag-icon.less";

const Flag = ({ countryCode, squared }) => (
  <span
    className={classNames("flag-icon", `flag-icon-${countryCode.toLowerCase()}`, {
      "flag-icon-squared": squared,
    })}
  />
);

Flag.defaultProps = {
  squared: false,
};

Flag.propTypes = {
  countryCode: PropTypes.string.isRequired,
  squared: PropTypes.bool,
};

export default Flag;
