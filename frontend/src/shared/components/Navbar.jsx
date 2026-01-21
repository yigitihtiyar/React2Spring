import { useTranslation } from "react-i18next";
import { AuthContext, useAuthDispatch, useAuthState } from "../state/Context";

export function Navbar() {
  const { t } = useTranslation();
  const authState = useAuthState();
  const  dispatch = useAuthDispatch();

 const onClickLogout = () => {
  dispatch({type:'logout-success'});
 }

  return (
    <>
      <nav className="navbar navbar-expand bg-body-tertiary shadow-sm">
        <div className="container-fluid">
          <Link className="navbar-brand" to="#">
            Hoaxify
          </Link>
          <ul className="navbar-nav">
            {authState.id === 0 && (
              <>
                <li className="navbar-item">
                  <Link className="nav-link" to="/login">
                    {t("login")}
                  </Link>
                </li>
                <li className="navbar-item">
                  <Link className="nav-link" to="/signup">
                    {t("signUp")}
                  </Link>
                </li>
              </>
            )}
            {authState.id > 0 && (
              <>
                <li className="navbar-item">
                  <Link className="nav-link" to={`/user/${authState.id}`}>
                    MyProfile
                  </Link>
                </li>
                <li className="navbar-item">
                  <span className="nav-link"
                   role="button"
                   onClick={onClickLogout}>
                    Logout
                    </span>
                </li>
              </>
            )}
          </ul>
        </div>
      </nav>
    </>
  );
}
