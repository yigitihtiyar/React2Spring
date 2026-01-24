import { Button } from "@/shared/components/Button";
import { AuthContext } from "@/shared/state/Context";
import { useSelector } from "react-redux";
import { useState } from "react";
import { Alert } from "@/shared/components/Alert";
import { ProfileImage } from "@/shared/components/ProfileImage";
import { UserEditForm } from "./UserEditForm";

export function ProfileCard({ user }) {
  //const authState = useAuthState();
  const authState = useSelector((store) => store.auth);
  const [editMode, setEditMode] = useState(false);

  const isEditButtonVisable = !editMode && authState.id === user.id;

  const visibleUsername =
    authState.id === user.id ? authState.username : user.username;

  return (
    <div className="card">
      <div className="card-header text-center">
        <ProfileImage width={200} />
      </div>
      <div className="card-body text-center">
        {editMode && <span className="fs-3 d-block">{visibleUsername}</span>}
        {isEditButtonVisable && (
          <Button onClick={() => setEditMode(true)}>Edit</Button>
        )}
        {editMode && <UserEditForm setEditMode={setEditMode} />}
      </div>
    </div>
  );
}
