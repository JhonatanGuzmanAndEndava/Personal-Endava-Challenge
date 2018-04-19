import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { withRouter, Link } from 'react-router-dom';
import './Navbar.css';
import SearchBar from '../SearchBar/SearchBar';

class Navbar extends Component {
  constructor() {
    super();
    this.state = {
      loggedIn: localStorage.getItem('token'),
    };
    this.goToLogin = this.goToLogin.bind(this);
    this.goToSignup = this.goToSignup.bind(this);
  }

  goToLogin() {
    this.props.history.push('/login');
  }

  goToSignup() {
    this.props.history.push('/signup');
  }

  render() {
    const buttons = !this.state.loggedIn ? (
      [
        <button className="ui primary button" key="1" onClick={this.goToLogin}>Log In</button>,
        <button className="ui primary button" key="2" onClick={this.goToSignup}>Sign In</button>,
      ]
    ) : (
      <button className="ui primary button">Log Out</button>
    );

    return (
      <div className="ui container">
        <div className="ui large secondary menu">
          <Link to="/" className="header item">
            Reader&apos;s Nest
          </Link>
          <div className="item">
            <SearchBar history={this.props.history} />
          </div>
          <div className="right item">
            {buttons}
          </div>
        </div>
      </div>
    );
  }
}

Navbar.propTypes = {
  history: PropTypes.shape({
    push: PropTypes.func,
  }).isRequired,
};

export default withRouter(Navbar);
