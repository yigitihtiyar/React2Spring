import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { activateUser } from "./api";
import { Alert } from "@/shared/components/Alert";

export function Activation() {
  const { token } = useParams();

  const [apiProgress, setApiProgress] = useState();
  const [successMessage, setSuccessMessage] = useState();
  const [errorMessage, SetErrorMessage] = useState();

  useEffect(() => {
    async function activate() {
      setApiProgress(true);
      try {
        const response = await activateUser(token);
        setSuccessMessage(response.data.message);
      } catch (axiosError) {
        SetErrorMessage(axiosError.response.data.message);
      } finally {
        setApiProgress(false);
      }
    }
    activate();
  }, []);

  return (
    <>
      {apiProgress && (
        <Alert styleType="secondary" center>
         <Spinner />
        </Alert>
      )}
      {successMessage && (
        <Alert>{successMessage}</Alert>
      )}
      {errorMessage && <Alert styleType="danger">{errorMessage}</Alert>}
    </>
  );
}
