import React, { useState } from "react";
import './App.css';
import Header from './Header';
import Home from "./Home";
import Login from "./Login"
import ProtectedRoutes from "./ProtectedRoutes";
import { Route, Routes } from "react-router-dom"

function App() {
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [token,setToken] = useState();
  
  return (
    <Routes>
      <Route path = "/" element = {<Login />} />
      <Route element = {<ProtectedRoutes />}>

        <Route path = "/home" element = {<Home />} /> 

      </Route>
    </Routes> 
  );
}

export default App;
