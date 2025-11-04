import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { HomePage } from "./pages/Home/index.jsx";
import { SignUp } from "./pages/SignUp/index.jsx";
import App from "../App.jsx";

export default createBrowserRouter([
  {
    path: "/",
    Component: App,
    children: [
      {
        path: "/home",
        Component: HomePage,
        errorElement: <div>Unexpected error</div>,
      },
      {
        path: "/signup",
        Component: SignUp,
      },
    ],
  },
]);
