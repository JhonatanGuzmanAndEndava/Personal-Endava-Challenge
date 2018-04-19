import React from 'react';

const Signup = () => (
  <form className="ui container form">
    <h1 className="header">Sign Up</h1>
    <div className="field">
      <label>Email</label>
      <input type="text" name="email" placeholder="email@email.com" />
    </div>
    <div className="field">
      <label>Password</label>
      <input type="password" name="password" placeholder="Password" />
    </div>
    <div className="field">
      <label>username</label>
      <input type="text" name="username" placeholder="Username" />
    </div>
    <div className="field">
      <label>First Name</label>
      <input type="text" name="first-name" placeholder="First Name" />
    </div>
    <div className="field">
      <label>Last Name</label>
      <input type="text" name="last-name" placeholder="Last Name" />
    </div>
    <button className="fluid ui primary button">Sign In</button>
  </form>
);

export default Signup;
