import React from "react";
import { notification } from "antd";
import yup from "form/yup";
import Form from "form/form";
import CountryInput from "form/country-input";
import TextInput from "form/text-input";
import SubmitButton from "form/submit-button";
import { COUNTRIES, DEFAULT_COUNTRY } from "constants/countries";
import useFetch from "../../../fetch/useFetch";
import authenticate from "../../../auth/authenticate";
import ApiUrl from "../../../fetch/ApiUrl";

const validationSchema = yup.object({
  username: yup.string().required().min(3).max(32),
  email: yup.string().email().required().max(128),
  countryCode: yup.string().required().oneOf(COUNTRIES).default(DEFAULT_COUNTRY),
  identityCode: yup.number().required(),
});

const showError = (message) =>
  notification.error({
    message: "Error",
    description: ["string", "number"].includes(typeof message) ? message : JSON.stringify(message),
  });

const SmartIdRegister = () => {
  const { postJSON } = useFetch();

  const onSubmit = ({ username, email, countryCode, identityCode }, { setSubmitting }) => {
    const initData = { username, email, countryCode, identityCode };

    postJSON(`${ApiUrl.smartId}/register/init`, initData)
      .then(({ verificationCode, sessionId, authenticationHash }) => {
        showError(verificationCode);

        const doData = { username, email, sessionId, authenticationHash };

        postJSON(`${ApiUrl.smartId}/register/do`, doData)
          .then(({ jwt }) => authenticate(jwt))
          .catch((error) => {
            showError(error);
            setSubmitting(false);
          });
      })
      .catch((error) => {
        showError(error);
        setSubmitting(false);
      });
  };

  return (
    <Form validationSchema={validationSchema} onSubmit={onSubmit} width="20%">
      <TextInput name="username" label="Username" />
      <TextInput name="email" label="E-mail address" />
      <CountryInput name="countryCode" label="Country" />
      <TextInput name="identityCode" label="National identity code" />
      <SubmitButton>Register</SubmitButton>
    </Form>
  );
};

export default SmartIdRegister;
