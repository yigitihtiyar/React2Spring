import { Link, Outlet } from "react-router-dom";
import { LanguageSelector } from "@/shared/components/LanguageSelector";
import { Navbar } from "@/shared/components/Navbar";
import { AuthenticationContext } from "./shared/state/context";


export function App() {

  return (
    <AuthenticationContext>
      <Navbar />
      <div className="container mt-3">
        <Outlet />
        <LanguageSelector />
      </div>
    </AuthenticationContext>
  );
}

export default App;
