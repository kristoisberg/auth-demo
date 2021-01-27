import React from "react";
import { Tabs as AntdTabs } from "antd";
import PropTypes from "prop-types";

const Tabs = ({ tabs, defaultTabCode }) => (
  <AntdTabs tabPosition="left" defaultActiveKey={defaultTabCode}>
    {tabs.map(({ code, name, component: Component }) => (
      <AntdTabs.TabPane tab={name} key={code}>
        <Component />
      </AntdTabs.TabPane>
    ))}
  </AntdTabs>
);

Tabs.propTypes = {
  tabs: PropTypes.arrayOf(
    PropTypes.shape({
      code: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      component: PropTypes.elementType.isRequired,
    })
  ).isRequired,
  defaultTabCode: PropTypes.string.isRequired,
};

export default Tabs;
