import React, { useContext } from "react";
import { useRouter } from "next/router";
import { Layout as AntdLayout, Menu, Breadcrumb } from "antd";
import PropTypes from "prop-types";
import Link from "components/link";
import "./layout.less";
import AuthenticationContext from "../../auth/AuthenticationContext";

const Layout = ({ children }) => {
  const router = useRouter();
  const { user } = useContext(AuthenticationContext);

  const activeMenuItem = {
    "/login": "login",
    "/register": "register",
    "/profile": "profile",
    "/logout": "logout",
  }[router.pathname];

  return (
    <AntdLayout className="layout">
      <AntdLayout.Header>
        <div className="logo" />

        <Menu theme="dark" mode="horizontal" style={{ lineHeight: "64px" }} selectedKeys={[activeMenuItem]}>
          <Menu.Item>nav 1</Menu.Item>
          <Menu.Item>nav 2</Menu.Item>
          <Menu.Item>nav 3</Menu.Item>

          {user && (
            <>
              <Menu.Item key="logout" style={{ float: "right" }}>
                <Link href="/logout">Log out</Link>
              </Menu.Item>

              <Menu.Item key="profile" style={{ float: "right" }}>
                <Link href="/profile">{user.username}</Link>
              </Menu.Item>
            </>
          )}

          {!user && (
            <>
              <Menu.Item key="register" style={{ float: "right" }}>
                <Link href="/register">Register</Link>
              </Menu.Item>

              <Menu.Item key="login" style={{ float: "right" }}>
                <Link href="/login">Login</Link>
              </Menu.Item>
            </>
          )}
        </Menu>
      </AntdLayout.Header>

      <AntdLayout.Content style={{ padding: "0 50px" }}>
        <Breadcrumb style={{ margin: "16px 0" }}>
          <Breadcrumb.Item>Home</Breadcrumb.Item>
          <Breadcrumb.Item>List</Breadcrumb.Item>
          <Breadcrumb.Item>App</Breadcrumb.Item>
        </Breadcrumb>

        <div className="site-layout-content">{children}</div>
      </AntdLayout.Content>

      <AntdLayout.Footer style={{ textAlign: "center" }}>Ant Design ©2018 Created by Ant UED</AntdLayout.Footer>
    </AntdLayout>
  );
};

Layout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default Layout;
