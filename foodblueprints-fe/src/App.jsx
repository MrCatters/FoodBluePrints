import './App.css';
import Header from './Header';
import Home from "./Home";
import Login from "./Login"
import ProtectedRoutes from "./ProtectedRoutes";
import { Route, Routes } from "react-router-dom"

function App() {
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
