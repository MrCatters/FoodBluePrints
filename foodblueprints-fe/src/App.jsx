import React, { useState } from "react";
import './App.css';
import Header from './Header';
import Register from "./Register";
import Home from "./Home";
import Login from "./Login"
import Profile from "./Profile";
import ProtectedRoutes from "./ProtectedRoutes";
import { Route, Routes } from "react-router-dom"
import { RequireAuth } from "react-auth-kit";

function App() {
  
  return (
    <Routes>
      <Route path = "/" element={<RequireAuth loginPath="/login">
          <Home />
      </RequireAuth>}></Route>
      <Route path = "/home" element={<RequireAuth loginPath="/login">
          <Home />
      </RequireAuth>}></Route>
      <Route path = "/profile" element={<RequireAuth loginPath="/login">
          <Profile />
      </RequireAuth>}></Route>
      <Route path = "/login" element = {<Login />} /> 
      <Route path = "/register" element = {<Register />} />
    </Routes> 
  );
}

export default App;
