import React from "react";
import PropTypes from "prop-types";
import Tabs from "components/tabs";
import Flag from "components/flag";

export const PASSWORD_TAB = "PASSWORD_TAB";
export const ID_CARD_TAB = "ID_CARD_TAB";
export const MOBILE_ID_TAB = "MOBILE_ID_TAB";
export const SMART_ID_TAB = "SMART_ID_TAB";

const TABS = [PASSWORD_TAB, ID_CARD_TAB, MOBILE_ID_TAB, SMART_ID_TAB];

const DEFAULT_TAB = PASSWORD_TAB;

const TAB_NAMES = {
  [PASSWORD_TAB]: "Password",
  [ID_CARD_TAB]: (
    <>
      ID Card <Flag countryCode="ee" />
    </>
  ),
  [MOBILE_ID_TAB]: (
    <>
      Mobile-ID <Flag countryCode="ee" /> <Flag countryCode="lt" />
    </>
  ),
  [SMART_ID_TAB]: (
    <>
      Smart-ID <Flag countryCode="ee" /> <Flag countryCode="lt" />
    </>
  ),
};

const AuthenticationTabs = ({ tabComponents }) => (
  <Tabs tabs={TABS} tabNames={TAB_NAMES} tabComponents={tabComponents} defaultTab={DEFAULT_TAB} />
);

AuthenticationTabs.propTypes = {
  tabComponents: PropTypes.object.isRequired,
};

export default AuthenticationTabs;
