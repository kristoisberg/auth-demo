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
  usernameOrEmail: yup.string().required(),
  password: yup.string().required(),
});

const showError = (message) =>
  notification.error({
    message: "Error",
    description: typeof message === "string" ? message : JSON.stringify(message),
  });

const PasswordLogin = () => {
  const { postJSON } = useFetch();

  const onSubmit = (data, { setSubmitting }) => {
    postJSON(`${ApiUrl.password}/login`, data)
      .then(({ jwt }) => authenticate(jwt))
      .catch((error) => {
        showError(error);
        setSubmitting(false);
      });
  };

  return (
    <Form validationSchema={validationSchema} onSubmit={onSubmit} width="20%">
      <TextInput name="usernameOrEmail" label="Username or e-mail address" />
      <PasswordInput name="password" label="Password" />
      <SubmitButton>Login</SubmitButton>
    </Form>
  );
};

export default PasswordLogin;
