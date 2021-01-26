import React from "react";
import { notification } from "antd";
import Form from "form/form";
import yup from "form/yup";
import CountryInput from "form/country-input";
import PhoneNumberInput from "form/phone-number-input";
import TextInput from "form/text-input";
import SubmitButton from "form/submit-button";
import { COUNTRIES, COUNTRY_PHONE_CODES, DEFAULT_COUNTRY } from "constants/countries";
import useFetch from "../../../fetch/useFetch";
import authenticate from "../../../auth/authenticate";
import ApiUrl from "../../../fetch/ApiUrl";

const validationSchema = yup.object({
  countryCode: yup.string().required().oneOf(COUNTRIES).default(DEFAULT_COUNTRY),
  phoneNumber: yup.number().required(),
  identityCode: yup.number().required(),
});

const showError = (message) =>
  notification.error({
    message: "Error",
    description: ["string", "number"].includes(typeof message) ? message : JSON.stringify(message),
  });

const MobileIdLogin = () => {
  const { postJSON } = useFetch();

  const onSubmit = ({ countryCode, phoneNumber, identityCode }, { setSubmitting }) => {
    const initData = {
      phoneNumber: `${COUNTRY_PHONE_CODES[countryCode]}${phoneNumber}`,
      identityCode,
    };

    postJSON(`${ApiUrl.mobileId}/login/init`, initData)
      .then(({ verificationCode, sessionId, authenticationHash }) => {
        showError(verificationCode);

        const doData = { sessionId, authenticationHash };

        postJSON(`${ApiUrl.mobileId}/login/do`, doData)
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
          <CountryInput name="countryCode" label="Country" />
          <PhoneNumberInput name="phoneNumber" label="Phone number" countryCode={values.countryCode} />
          <TextInput name="identityCode" label="National identity code" />
          <SubmitButton>Login</SubmitButton>
        </>
      )}
    </Form>
  );
};

export default MobileIdLogin;
