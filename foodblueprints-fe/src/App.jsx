import React, { useState } from "react";
import './App.css';
import Header from './Header';
import Home from "./Home";
import Login from "./Login"
import ProtectedRoutes from "./ProtectedRoutes";
import { Route, Routes } from "react-router-dom"

function App() {
  
  return (
    <Routes>
      
      <Route path = "/" element = {<Home />} />
      <Route path = "/login" element = {<Login />} /> 

     
    </Routes> 
  );
}

export default App;
