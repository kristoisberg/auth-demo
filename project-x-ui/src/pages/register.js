import React from "react";
import Link from "components/link";
import AuthenticationTabs, {
  PASSWORD_TAB,
  ID_CARD_TAB,
  MOBILE_ID_TAB,
  SMART_ID_TAB,
} from "components/authentication/tabs";
import {
  PasswordRegister,
  IdCardRegister,
  MobileIdRegister,
  SmartIdRegister,
} from "components/authentication/register";

const TAB_COMPONENTS = {
  [PASSWORD_TAB]: PasswordRegister,
  [ID_CARD_TAB]: IdCardRegister,
  [MOBILE_ID_TAB]: MobileIdRegister,
  [SMART_ID_TAB]: SmartIdRegister,
};

const RegisterPage = () => (
  <>
    <span style={{ float: "right" }}>
      <Link href="/login">Already have an account?</Link>
    </span>
    <br style={{ clear: "both", marginTop: "1em" }} />
    <AuthenticationTabs tabComponents={TAB_COMPONENTS} />
  </>
);

export default RegisterPage;
