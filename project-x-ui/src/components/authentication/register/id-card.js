import React from "react";
import yup from "form/yup";
import Form from "form/form";
import TextInput from "form/text-input";
import SubmitButton from "form/submit-button";

const validationSchema = yup.object({
  username: yup.string().required().min(3).max(32),
  email: yup.string().email().required().max(128),
});

const IdCardRegister = () => {
  const onSubmit = (data) => {
    // eslint-disable-next-line no-console
    console.log(data);
  };

  return (
    <Form validationSchema={validationSchema} onSubmit={onSubmit} width="20%">
      <TextInput name="username" label="Username" />
      <TextInput name="email" label="E-mail address" />
      <SubmitButton>Register</SubmitButton>
    </Form>
  );
};

export default IdCardRegister;
