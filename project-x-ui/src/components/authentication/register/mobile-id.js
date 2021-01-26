import React from "react";
import { notification } from "antd";
import yup from "form/yup";
import Form from "form/form";
import CountryInput from "form/country-input";
import PhoneNumberInput from "form/phone-number-input";
import TextInput from "form/text-input";
import SubmitButton from "form/submit-button";
import { COUNTRIES, COUNTRY_PHONE_CODES, DEFAULT_COUNTRY } from "constants/countries";
import useFetch from "../../../fetch/useFetch";
import authenticate from "../../../auth/authenticate";
import ApiUrl from "../../../fetch/ApiUrl";

const validationSchema = yup.object({
  username: yup.string().required().min(3).max(32),
  email: yup.string().email().required().max(128),
  countryCode: yup.string().required().oneOf(COUNTRIES).default(DEFAULT_COUNTRY),
  phoneNumber: yup.number().required(),
  identityCode: yup.number().required(),
});

const showError = (message) =>
  notification.error({
    message: "Error",
    description: ["string", "number"].includes(typeof message) ? message : JSON.stringify(message),
  });

const MobileIdRegister = () => {
  const { postJSON } = useFetch();

  const onSubmit = ({ username, email, countryCode, phoneNumber, identityCode }, { setSubmitting }) => {
    const initData = {
      username,
      email,
      phoneNumber: `${COUNTRY_PHONE_CODES[countryCode]}${phoneNumber}`,
      identityCode,
    };

    postJSON(`${ApiUrl.mobileId}/register/init`, initData)
      .then(({ verificationCode, sessionId, authenticationHash }) => {
        showError(verificationCode);

        const doData = { username, email, sessionId, authenticationHash };

        postJSON(`${ApiUrl.mobileId}/register/do`, doData)
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
      {({ values }) => (
        <>
          <TextInput name="username" label="Username" />
          <TextInput name="email" label="E-mail address" />
          <CountryInput name="countryCode" label="Country" />
          <PhoneNumberInput name="phoneNumber" label="Phone number" countryCode={values.countryCode} />
          <TextInput name="identityCode" label="National identity code" />
          <SubmitButton>Register</SubmitButton>
        </>
      )}
    </Form>
  );
};

export default MobileIdRegister;
