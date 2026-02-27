import { Alert } from "@/shared/components/Alert";

export function PasswordResetRequest() {
  const { onSubmit, onChangeEmail, apiProgress, success, error, generalerror } =
    PasswordResetRequest();

  return (
    <div className="container">
      <div className="col-lg-6 offset-lg-3 col-sm-8 offset-sm-2">
        <form className="card" onSubmit={onSubmit}>
          <div className="card-header text-center">
            <span className="gs-3">Reset Your Password</span>
          </div>
          <div className="card-body">
            <Input
              id="email"
              label="E-mail"
              error={error}
              onChange={onChangeEmail}
            />
            {success && <Alert>{success}</Alert>}
            {generalerror && (<Alert styleType="danger">{generalerror}</Alert>)}
            <div className="text-center">
              <Button apiProgress={apiProgress}>Reset</Button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
