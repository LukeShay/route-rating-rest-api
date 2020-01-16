import { Button } from "@material-ui/core";
import React from "react";
import Form from "../../common/forms/Form";
import Input from "../../common/inputs/Input";
import Selector from "../../common/inputs/Select";
import TransitionModal from "../../common/modal/Modal";

export interface IRatingAddModalProps {
  routeId: string;
  open: boolean;
  handleClose(): Promise<void> | void;
}

const RatingAddModal: React.FunctionComponent<IRatingAddModalProps> = ({
  routeId,
  open,
  handleClose
}): JSX.Element => {
  const [rating, setRating] = React.useState<number>(0);
  const [grade, setGrade] = React.useState<string>("");
  const [review, setReview] = React.useState<string>("");
  const [reviewMessage, setReviewMessage] = React.useState<string>(
    "0/140 characters used."
  );

  const handleChange = async (event: any): Promise<void> => {
    const { id, value } = event.target;

    if (id === "rating") {
      setRating(value);
    } else if (id === "grade") {
      setGrade(value);
    } else if (id === "review" && value.length <= 140) {
      setReviewMessage(value.length + "/140 characters used.");
      setReview(value);
    }
  };

  const FormHead: JSX.Element = (
    <div style={{ display: "inline" }}>
      <div style={{ float: "left", marginRight: "25px", marginTop: "5px" }}>
        Add rating
      </div>
      <div style={{ float: "right", marginLeft: "25px" }}>
        <Button onClick={handleClose} type="button" variant="outlined">
          Cancel
        </Button>
      </div>
    </div>
  );

  const FormBody: JSX.Element = (
    <React.Fragment>
      <Selector
        handleChange={handleChange}
        value={rating}
        id="rating"
        name="rating-select"
        label="Rating"
        native={true}
      >
        <option value="" />
        <option value={1}>1</option>
        <option value={2}>2</option>
        <option value={3}>3</option>
        <option value={4}>4</option>
        <option value={5}>5</option>
      </Selector>
      <Selector
        handleChange={handleChange}
        value={grade}
        id="grade"
        name="grade-select"
        label="Grade"
        native={true}
      >
        <option value="" />
        <option value={"GRADE_5_5"}>5.5</option>
        <option value={"GRADE_5_6"}>5.6</option>
        <option value={"GRADE_5_7"}>5.7</option>
        <option value={"GRADE_5_8"}>5.8</option>
        <option value={"GRADE_5_9"}>5.9</option>
        <option value={"GRADE_5_10ab"}>5.10ab</option>
        <option value={"GRADE_5_10cd"}>5.10cd</option>
        <option value={"GRADE_5_11ab"}>5.11ab</option>
        <option value={"GRADE_5_11cd"}>5.11cd</option>
        <option value={"GRADE_5_12ab"}>5.12ab</option>
        <option value={"GRADE_5_12cd"}>5.12cd</option>
        <option value={"GRADE_5_13ab"}>5.13ab</option>
        <option value={"GRADE_5_13cd"}>5.13cd</option>
        <option value={"GRADE_5_14ab"}>5.14ab</option>
        <option value={"GRADE_5_14cd"}>5.14cd</option>
        <option value={"GRADE_5_15ab"}>5.15ab</option>
        <option value={"GRADE_5_15cd"}>5.15cd</option>
      </Selector>
      <Input
        autoCapitalize={"true"}
        fullWidth={true}
        value={review}
        handleChange={handleChange}
        type="text"
        id="review"
        rows={4}
        helpText={reviewMessage}
        error={review.length === 140}
      />
    </React.Fragment>
  );
  return (
    <TransitionModal
      open={open}
      handleClose={handleClose}
      style={{ width: "475px" }}
    >
      <Form
        title={FormHead}
        formInputs={FormBody}
        buttonText="Save"
        handleSubmit={(event: any): void => {
          event.preventDefault();
        }}
      />
    </TransitionModal>
  );
};

export default RatingAddModal;
