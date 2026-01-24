import { Button } from "@/shared/components/Button";
import { AuthContext, useAuthDispatch } from "@/shared/state/Context";
import { useState } from "react";
import { Input } from "@/shared/components/Input";
import { useTranslation } from "react-i18next";
import { Alert } from "@/shared/components/Alert";
import { useSelector } from "react-redux";
import { updateUser } from "./api";

export function UserEditForm({ setEditMode }) {
  const { t } = useTranslation();
  const authState = useSelector((store) => store.auth);
  const [newUsername, setNewUsername] = useState(authState.username);
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({});
  const [generalerror, setGeneralError] = useState();
  const dispatch = useAuthDispatch();

  const onChangeUsername = (event) => {
    setNewUsername(event.target.value);
    setErrors({});
  };

  const onClickCancel = () => {
    setEditMode(false);
    setNewUsername(authState.username);
  };

  const onClickSave = async () => {
    setApiProgress(true);
    setErrors({});
    setGeneralError();
    try {
      await updateUser(authState.id, { username: newUsername });
      dispatch({
        type: "user-update-success",
        data: { username: newUsername },
      });
    } catch (axiosError) {
      if (axiosError.response?.data) {
        if (axiosError.response.data.status === 400) {
          setErrors(axiosError.response.data.validationErrors);
        } else {
          setGeneralError(axiosError.response.data.message);
        }
      } else {
        setGeneralError(t("genericError"));
      }
    } finally {
      setApiProgress(false);
    }
  };

  return (
    <>
      <Input
        label={t("username")}
        defultValue={authState.username}
        onChange={onChangeUsername}
        error={errors.username}
      />
      {generalerror && <Alert styleType="danger">{generalerror}</Alert>}
      <Button apiProgress={apiProgress} onClick={onClickSave}>
        Save
      </Button>
      <div className="d-inline m-1"></div>
      <Button styleType="outline-secondary" onClick={onClickCancel}>
        Cancel
      </Button>
    </>
  );
}
