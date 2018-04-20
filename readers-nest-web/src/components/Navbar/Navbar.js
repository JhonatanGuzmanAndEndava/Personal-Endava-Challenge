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
    this.goToNewBookclub = this.goToNewBookclub.bind(this);
    this.logout = this.logout.bind(this);
  }

  componentDidUpdate(prevProps) {
    if (this.props.location.pathname !== prevProps.location.pathname) {
      this.onRouteChanged();
    }
  }

  onRouteChanged() {
    const actToken = localStorage.getItem('token');
    if (this.state.loggedIn !== actToken) {
      this.setState({
        loggedIn: actToken,
      });
    }
  }

  goToLogin() {
    this.props.history.push('/login');
  }

  goToSignup() {
    this.props.history.push('/signup');
  }

  goToNewBookclub() {
    this.props.history.push('/new/bookclub');
  }

  logout() {
    localStorage.removeItem('user-id');
    localStorage.removeItem('token');
    localStorage.removeItem('refresh-token');

    this.setState({
      loggedIn: '',
    });
  }

  render() {
    const buttons = !this.state.loggedIn ? (
      [
        <button className="ui primary button" key="1" onClick={this.goToLogin}>Log In</button>,
        <button className="ui primary button" key="2" onClick={this.goToSignup}>Sign In</button>,
      ]
    ) : (
      [
        <button className="positive ui labeled icon button" onClick={this.goToNewBookclub} key="1">
          <i className="plus icon" />
          Bookclub
        </button>,
        <button className="ui primary button" onClick={this.logout} key="2">Log Out</button>,
      ]
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
  location: PropTypes.shape({
    pathname: PropTypes.string,
  }).isRequired,
};

export default withRouter(Navbar);
