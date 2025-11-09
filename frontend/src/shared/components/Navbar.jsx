import { useTranslation } from "react-i18next";

export function Navbar() {

   const {t} = useTranslation();
  return (
    <>
      <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
        <div className="container-fluid">
          <Link className="navbar-brand" to="#">
            Hoaxify
          </Link>
          <ul className="navbar-nav">
            <li className="navbar-item">
              <Link className="nav-link" to="/signup">
                {t("signUp")}
              </Link>
            </li>
          </ul>
        </div>
      </nav>
    </>
  );
}
