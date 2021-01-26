import React from "react";
import { Tabs as AntdTabs } from "antd";
import PropTypes from "prop-types";

const Tabs = ({ tabs, tabNames, tabComponents, defaultTab }) => (
  <AntdTabs tabPosition="left" defaultActiveKey={defaultTab}>
    {tabs.map((tab) => {
      const Component = tabComponents[tab];

      return (
        <AntdTabs.TabPane tab={tabNames[tab]} key={tab}>
          <Component />
        </AntdTabs.TabPane>
      );
    })}
  </AntdTabs>
);

Tabs.propTypes = {
  tabs: PropTypes.array.isRequired,
  tabNames: PropTypes.object.isRequired,
  tabComponents: PropTypes.object.isRequired,
  defaultTab: PropTypes.string.isRequired,
};

export default Tabs;
