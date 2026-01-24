import { createContext } from "react";
import { loadAuthState, storeAuthState } from "./storage";
import { useReducer } from "react";
import { useEffect } from "react";
import { useContext } from "react";
import { setToken } from "@/lib/http";

// eslint-disable-next-line react-refresh/only-export-components
export const AuthContext = createContext();

// eslint-disable-next-line react-refresh/only-export-components
export const AuthDispatchContext = createContext();

// eslint-disable-next-line react-refresh/only-export-components
export function useAuthState() {
  return useContext(AuthContext);
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuthDispatch() {
  return useContext(AuthDispatchContext);
}

const authReducer = (authState, action) => {
  switch (action.type) {
    case "login-success":
      setToken(action.data.token);
      return action.data.user;

    case "logout-success":
      return { id: 0 };

    default:
      throw new Error(`unkown action: ${action.type}`);
  }
};

export function AuthenticationContext({ children }) {
  const [authState, dispatch] = useReducer(authReducer, loadAuthState());

  useEffect(() => {
    storeAuthState(authState);
  }, [authState]);

  return (
    <AuthContext.Provider value={authState}>
      <AuthDispatchContext.Provider value={dispatch}>
        {children}
      </AuthDispatchContext.Provider>
    </AuthContext.Provider>
  );
}
