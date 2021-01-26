import React from "react";
import { Button } from "antd";
import { useFormikContext } from "formik";
import PropTypes from "prop-types";

const SubmitButton = ({ children }) => {
  const { isSubmitting } = useFormikContext();

  return (
    <div style={{ textAlign: "center" }}>
      <Button type="primary" htmlType="submit" loading={isSubmitting}>
        {children || "Submit"}
      </Button>
    </div>
  );
};

SubmitButton.propTypes = {
  children: PropTypes.node.isRequired,
};

export default SubmitButton;
