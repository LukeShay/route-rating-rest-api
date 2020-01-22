import React from "react";
import * as ReactRouter from "react-router";
import { toast } from "react-toastify";
import * as GymsActions from "../../../context/gyms/gymsActions";
import { useGymsContext } from "../../../context/gyms/gymsStore";
import { useUserContext } from "../../../context/user/userStore";
import { Routes } from "../../../routes";
import { Gym } from "../../../types";
import GymEditForm from "./GymEditForm";

const GymEditPage: React.FC = (): JSX.Element => {
  const history = ReactRouter.useHistory();

  const [gym, setGym] = React.useState<Gym>({} as Gym);

  const { state: gymsState, dispatch: gymsDispatch } = useGymsContext();
  const { state: userState } = useUserContext();

  React.useEffect(() => {
    const gymId = history.location.pathname
      .split("/")
      .splice(-1)
      .pop();

    const tempGym = gymsState.gyms
      .filter((element) => element.id === gymId)
      .pop();

    const { user } = userState;

    if (
      tempGym &&
      user &&
      tempGym.authorizedEditors &&
      tempGym.authorizedEditors.find(
        (editorId: string) => editorId === user.userId
      )
    ) {
      setGym(tempGym);
    } else {
      history.push(Routes.GYMS + "/" + gymId);
    }
  }, [gymsState, history, userState]);

  const handleSubmit = (updatedGym: Gym, photo: File | null): void => {
    GymsActions.updateGym(
      gymsDispatch,
      {
        id: gym.id,
        ...updatedGym
      } as Gym,
      gym
    ).then((responseOne) => {
      if (!photo) {
        if (responseOne instanceof Response && responseOne.status === 200) {
          toast.success("Gym updated.");
        } else {
          toast.error("Error updating gym.");
        }
      } else {
        GymsActions.updateGymPhoto(gymsDispatch, photo, gym).then(
          (responseTwo) => {
            if (responseTwo instanceof Response && responseTwo.status === 200) {
              toast.success("Gym updated.");
            } else {
              toast.error("Error updating photo for gym.");
            }
          }
        );
      }
    });
  };

  if (gym && gym.id) {
    return <GymEditForm gym={gym} handleSubmit={handleSubmit} />;
  } else {
    return <div />;
  }
};

export default GymEditPage;
