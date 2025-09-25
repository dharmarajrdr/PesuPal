import { useEffect, useRef } from "react";
import { gsap } from "gsap";
import "./ProgressSteps.css";

export default function ProgressSteps({
    steps = ["Select", "Review", "Payment", "Success"],
    currentStep = 0,
    onStepClick,
    completedColor = "var(--success-color)",
    activeColor = "var(--primary-color)",
    inactiveColor = "var(--bg-color)"
}) {

    const boxThreeRef = useRef(null);
    const barFillRef = useRef(null);
    const stepRefs = useRef([]);

    useEffect(() => {
        if (barFillRef.current) {
            gsap.to(barFillRef.current, {
                width: `${(currentStep / (steps.length - 1)) * 100}%`,
                duration: 0.6,
                ease: "power2.out",
            });
        }

        stepRefs.current.forEach((step, index) => {
            if (!step) return;
            step.classList.remove("point--active", "point--complete");

            if (index < currentStep) {
                step.classList.add("point--complete");
            } else if (index === currentStep) {
                step.classList.add("point--active");
            }
        });
    }, [currentStep]);

    return (
        <div className="boxes">
            <div className="box" ref={boxThreeRef}>
                <div className="progress">
                    <div className="bar">
                        <div className="bar__fill" ref={barFillRef}></div>
                    </div>

                    {steps.map((step, index) => (
                        <div
                            key={index}
                            ref={(el) => (stepRefs.current[index] = el)}
                            className="point"
                            onClick={() => onStepClick && onStepClick(index)}
                        >
                            <div className="bullet"></div>
                            <label className="label">{step}</label>
                        </div>
                    ))}
                </div>
            </div>
        </div>

    );
}
