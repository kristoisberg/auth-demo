import React from "react";
import Link from "components/link";
import { PasswordRegister, MobileIdRegister, SmartIdRegister } from "components/register";
import Tabs from "components/tabs";
import Flag from "components/flag";

export const PASSWORD_TAB = "PASSWORD_TAB";
export const MOBILE_ID_TAB = "MOBILE_ID_TAB";
export const SMART_ID_TAB = "SMART_ID_TAB";

const TABS = [
  { code: PASSWORD_TAB, name: "Password", component: PasswordRegister },
  {
    code: MOBILE_ID_TAB,
    name: (
      <>
        Mobile-ID <Flag countryCode="ee" /> <Flag countryCode="lt" />
      </>
    ),
    component: MobileIdRegister,
  },
  {
    code: SMART_ID_TAB,
    name: (
      <>
        Smart-ID <Flag countryCode="ee" /> <Flag countryCode="lt" />
      </>
    ),
    component: SmartIdRegister,
  },
];

const RegisterPage = () => (
  <>
    <span style={{ float: "right" }}>
      <Link href="/login">Already have an account?</Link>
    </span>
    <br style={{ clear: "both", marginTop: "1em" }} />
    <Tabs tabs={TABS} defaultTabCode={PASSWORD_TAB} />
  </>
);

export default RegisterPage;
