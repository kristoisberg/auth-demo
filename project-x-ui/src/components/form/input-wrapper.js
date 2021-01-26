import React from "react";
import { useFormikContext } from "formik";
import { FormItem } from "formik-antd";
import PropTypes from "prop-types";
import { isRequired } from "./utils";

const InputWrapper = ({ name, label, plain, children }) => {
  const { validationSchema } = useFormikContext();

  if (plain) {
    return <div data-name={name}>{children}</div>;
  }

  return (
    <div style={{ paddingBottom: "1em" }}>
      <FormItem name={name} data-name={name} label={label} required={isRequired(validationSchema, name)}>
        {children}
      </FormItem>
    </div>
  );
};

InputWrapper.propTypes = {
  name: PropTypes.string.isRequired,
  label: PropTypes.string.isRequired,
  plain: PropTypes.bool.isRequired,
  children: PropTypes.node.isRequired,
};

export default InputWrapper;
