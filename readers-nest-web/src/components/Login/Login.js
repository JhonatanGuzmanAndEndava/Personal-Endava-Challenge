import React from 'react';

const Login = () => (
  <form className="ui container form">
    <h1 className="header">Login</h1>
    <div className="field">
      <label>Email</label>
      <input type="text" name="email" placeholder="email@email.com" />
    </div>
    <div className="field">
      <label>Password</label>
      <input type="password" name="password" placeholder="Type your passsword..." />
    </div>
    <button className="fluid ui primary button">Login</button>
  </form>
);

export default Login;
