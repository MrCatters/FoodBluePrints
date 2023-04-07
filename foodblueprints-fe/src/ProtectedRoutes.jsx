import { Outlet } from "react-router-dom";
import Login from "./Login";

const useAuth = () => {
    return false;
}

function ProtectedRoutes(props) {
    const isAuth = useAuth();
    return isAuth ? <Outlet /> : <Login />;
}

export default ProtectedRoutes;