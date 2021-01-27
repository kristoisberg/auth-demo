import React from "react";
import { notification } from "antd";
import Form from "form/form";
import yup from "form/yup";
import CountryInput from "form/country-input";
import TextInput from "form/text-input";
import SubmitButton from "form/submit-button";
import { COUNTRIES, DEFAULT_COUNTRY } from "constants/countries";
import useFetch from "../../fetch/useFetch";
import authenticate from "../../auth/authenticate";
import ApiUrl from "../../fetch/ApiUrl";

const validationSchema = yup.object({
  countryCode: yup.string().required().oneOf(COUNTRIES).default(DEFAULT_COUNTRY),
  identityCode: yup.number().required(),
});

const showError = (message) =>
  notification.error({
    message: "Error",
    description: ["string", "number"].includes(typeof message) ? message : JSON.stringify(message),
  });

const SmartIdLogin = () => {
  const { postJSON } = useFetch();

  const onSubmit = ({ countryCode, identityCode }, { setSubmitting }) => {
    const initData = { countryCode, identityCode };

    postJSON(`${ApiUrl.smartId}/login/init`, initData)
      .then(({ verificationCode, sessionId, authenticationHash }) => {
        showError(verificationCode);

        const doData = { sessionId, authenticationHash };

        postJSON(`${ApiUrl.smartId}/login/do`, doData)
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
      <CountryInput name="countryCode" label="Country" />
      <TextInput name="identityCode" label="National identity code" />
      <SubmitButton>Login</SubmitButton>
    </Form>
  );
};

export default SmartIdLogin;
