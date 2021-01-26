import React from "react";
import Link from "components/link";
import AuthenticationTabs, {
  PASSWORD_TAB,
  ID_CARD_TAB,
  MOBILE_ID_TAB,
  SMART_ID_TAB,
} from "components/authentication/tabs";
import { PasswordLogin, IdCardLogin, MobileIdLogin, SmartIdLogin } from "components/authentication/login";

const TAB_COMPONENTS = {
  [PASSWORD_TAB]: PasswordLogin,
  [ID_CARD_TAB]: IdCardLogin,
  [MOBILE_ID_TAB]: MobileIdLogin,
  [SMART_ID_TAB]: SmartIdLogin,
};

const LoginPage = () => (
  <>
    <span style={{ float: "right" }}>
      <Link href="/register">Don&apos;t have an account yet?</Link>
    </span>
    <br style={{ clear: "both", marginTop: "1em" }} />
    <AuthenticationTabs tabComponents={TAB_COMPONENTS} />
  </>
);

export default LoginPage;
