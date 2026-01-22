export function storeAuthState(auth) {
  localStorage.setItem("auth", JSON.stringify(auth));
}

export function loadAuthState() {
  const defaultState = { id: 0 };
  const authStateInStorage = localStorage.getItem("auth");
  if (!authStateInStorage) return defaultState;
  try {
    return JSON.parse(authStateInStorage);
  } catch {
    return defaultState;
  }
}


 export function cardState(card)
 {
   localStorage.setItem("card",JSON.stringify(card));
 }

 export function loadCardState()
 {
   const defaultState = {items : {} , totalPrice:0};
   const cardStateInStorage = localStorage.getItem("card");
  if(!cardStateInStorage) return defaultState;
  try {
     return JSON.parse(cardStateInStorage);
   } catch {
     return defaultState;   
    }
 }