import React from "react";
import PropTypes from "prop-types";

const Profile = ({ user }) => <div className="container">{JSON.stringify(user)}</div>;

Profile.propTypes = {
  user: PropTypes.object,
};

Profile.defaultProps = {
  user: null,
};

export default Profile;
