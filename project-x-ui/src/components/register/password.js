import React from "react";
import { notification } from "antd";
import Form from "form/form";
import yup from "form/yup";
import TextInput from "form/text-input";
import PasswordInput from "form/password-input";
import SubmitButton from "form/submit-button";
import authenticate from "../../auth/authenticate";
import useFetch from "../../fetch/useFetch";
import ApiUrl from "../../fetch/ApiUrl";

const validationSchema = yup.object({
  username: yup.string().required().min(3).max(32),
  email: yup.string().email().required().max(128),
  password: yup.string().required().min(10),
  confirmPassword: yup.string().required().equalTo(yup.ref("password")),
});

const showError = (message) =>
  notification.error({
    message: "Error",
    description: typeof message === "string" ? message : JSON.stringify(message),
  });

const PasswordRegister = () => {
  const { postJSON } = useFetch();

  const onSubmit = (data, { setSubmitting }) => {
    postJSON(`${ApiUrl.password}/register`, data)
      .then(({ jwt }) => authenticate(jwt))
      .catch((error) => {
        showError(error);
        setSubmitting(false);
      });
  };

  return (
    <Form validationSchema={validationSchema} onSubmit={onSubmit} width="20%">
      <TextInput name="username" label="Username" />
      <TextInput name="email" label="E-mail address" />
      <PasswordInput name="password" label="Password" />
      <PasswordInput name="confirmPassword" label="Confirm password" />
      <SubmitButton>Register</SubmitButton>
    </Form>
  );
};

export default PasswordRegister;
