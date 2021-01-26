import React from "react";
import Form from "form/form";
import yup from "form/yup";
import SubmitButton from "form/submit-button";

const validationSchema = yup.object({});

const IdCardLogin = () => {
  const onSubmit = (data) => {
    // eslint-disable-next-line no-console
    console.log(data);
  };

  return (
    <Form validationSchema={validationSchema} onSubmit={onSubmit} width="20%">
      <SubmitButton>Login</SubmitButton>
    </Form>
  );
};

export default IdCardLogin;
