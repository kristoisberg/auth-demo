/* eslint-disable no-nested-ternary */
import React, { useEffect, useState, useRef } from "react";
import { Formik } from "formik";
import { Form as AntdForm } from "formik-antd";
import PropTypes from "prop-types";
import { getPaths } from "./utils";

const Form = ({ children, validationSchema, initialValues, width, ...props }) => (
  <div style={{ width, marginLeft: "auto", marginRight: "auto" }}>
    <Formik
      validationSchema={validationSchema}
      initialValues={{ ...validationSchema.cast(), ...initialValues }}
      {...props}
    >
      {(renderProps) => <FormInner {...renderProps}>{children}</FormInner>}
    </Formik>
  </div>
);

const FormInner = ({ children, ...props }) => {
  const { isSubmitting, isValidating, errors } = props;
  const [firstError, setFirstError] = useState(null);
  const formContainerRef = useRef(null);

  useEffect(() => {
    if (isValidating || !isSubmitting) {
      setFirstError(null);
    } else {
      const paths = getPaths(errors);
      setFirstError(paths.length ? paths[0] : null);
    }
  }, [errors, isSubmitting, isValidating]);

  useEffect(() => {
    if (firstError) {
      const selector = `[data-name="${firstError}"]`;
      const formContainer = formContainerRef.current;
      const element = formContainer.querySelector(selector);

      if (element) {
        element.scrollIntoView({ behavior: "smooth" });
      }
    }
  }, [firstError]);

  return (
    <AntdForm layout="vertical">
      <div ref={formContainerRef}>
        {children ? (typeof children === "function" ? children(props) : children) : null}
      </div>
    </AntdForm>
  );
};

Form.defaultProps = {
  width: "100%",
  initialValues: {},
};

Form.propTypes = {
  children: PropTypes.oneOfType([PropTypes.node, PropTypes.func]).isRequired,
  validationSchema: PropTypes.object.isRequired,
  initialValues: PropTypes.object,
  width: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

export default Form;
