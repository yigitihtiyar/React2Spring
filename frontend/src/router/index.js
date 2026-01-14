import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { HomePage } from "./pages/Home/index.jsx";
import { SignUp } from "./pages/SignUp/index.jsx";
import App from "../App.jsx";
import { Activation } from "../pages/Activation/index.jsx";
import { Component } from "react";
import { User } from "@/pages/User/index.jsx";

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
      {
        path: "/activation/:token",
        Component: Activation
      },
      {
        path: "/user/:id",
        Component: User,
      } 
    ],
  },
]);
