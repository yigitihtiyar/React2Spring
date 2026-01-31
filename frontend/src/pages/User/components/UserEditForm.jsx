import { Button } from "@/shared/components/Button";
import { AuthContext, useAuthDispatch } from "@/shared/state/Context";
import { useState } from "react";
import { Input } from "@/shared/components/Input";
import { useTranslation } from "react-i18next";
import { Alert } from "@/shared/components/Alert";
import { useSelector } from "react-redux";
import { updateUser } from "./api";

export function UserEditForm({ setEditMode, setTempImage }) {
  const { t } = useTranslation();
  const authState = useSelector((store) => store.auth);
  const [newUsername, setNewUsername] = useState(authState.username);
  const [apiProgress, setApiProgress] = useState(false);
  const [errors, setErrors] = useState({});
  const [generalerror, setGeneralError] = useState();
  const dispatch = useAuthDispatch();
  const [newImage, setNewImage] = useState();

  const onChangeUsername = (event) => {
    setNewUsername(event.target.value);
    setErrors({});
  };

  const onClickCancel = () => {
    setEditMode(false);
    setNewUsername(authState.username);
    setNewImage();
    setTempImage();
  };

  const onSelectImage = (event) => 
    {
    if(event.target.files.length<1) return;
     const file = event.target.files[0];
     const fileReader = new FileReader();

     fileReader.onloadend = () => {
         const data = fileReader.result
         setNewImage(data);
         setTempImage(data);
     }
     fileReader.readAsDataURL(file);
     
    }

  const onSubmit= async (event) => {
    event.preventDefault();
    setApiProgress(true);
    setErrors({});
    setGeneralError();
    try {
      const {data} = await updateUser(authState.id, { username: newUsername, image: newImage});
      dispatch({
        type: "user-update-success",
        data: { username: data.newUsername, image: data.newImage },
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
    <form onSubmit={onSubmit}>
      <Input
        label={t("username")}
        defultValue={authState.username}
        onChange={onChangeUsername}
        error={errors.username}
      />
      <Input
      label = "Profile Image"
      type = "file"
      onChange = {onSelectImage}
      
      ></Input>
      {generalerror && <Alert styleType="danger">{generalerror}</Alert>}
      <Button apiProgress={apiProgress}  type="submit">
        Save
      </Button>
      <div className="d-inline m-1"></div>
      <Button styleType="outline-secondary" onClick={onClickCancel} type="button">
        Cancel
      </Button>
    </form>
  );
}
